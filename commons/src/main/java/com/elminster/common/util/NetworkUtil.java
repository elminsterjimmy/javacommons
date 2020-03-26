package com.elminster.common.util;

import com.elminster.common.constants.RegexConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * The Network Utilities.
 *
 * @author jgu
 * @version 1.0
 */
public abstract class NetworkUtil {

  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

  /** Map for all valid subnet masks and their lengths. */
  private static final Map<String, Long> VALID_SUBNET_MASKS = new HashMap<>(33);
  /** Regex for IPV4. */
  private static final Pattern IPV4_PATTERN = Pattern.compile
      ("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
  /** Regex to determine if a hostname is valid. */
  private static final Pattern HOSTNAME_PATTERN = Pattern.compile
      ("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

  private static final int MAX_PORT = 65535;

  static {
    VALID_SUBNET_MASKS.put("255.255.255.255", 1L);
    VALID_SUBNET_MASKS.put("255.255.255.254", 2L);
    VALID_SUBNET_MASKS.put("255.255.255.252", 4L);
    VALID_SUBNET_MASKS.put("255.255.255.248", 8L);
    VALID_SUBNET_MASKS.put("255.255.255.240", 16L);
    VALID_SUBNET_MASKS.put("255.255.255.224", 32L);
    VALID_SUBNET_MASKS.put("255.255.255.192", 64L);
    VALID_SUBNET_MASKS.put("255.255.255.128", 128L);
    VALID_SUBNET_MASKS.put("255.255.255.0", 256L);
    VALID_SUBNET_MASKS.put("255.255.254.0", 512L);
    VALID_SUBNET_MASKS.put("255.255.252.0", 1024L);
    VALID_SUBNET_MASKS.put("255.255.248.0", 2048L);
    VALID_SUBNET_MASKS.put("255.255.240.0", 4096L);
    VALID_SUBNET_MASKS.put("255.255.224.0", 8192L);
    VALID_SUBNET_MASKS.put("255.255.192.0", 16384L);
    VALID_SUBNET_MASKS.put("255.255.128.0", 32768L);
    VALID_SUBNET_MASKS.put("255.255.0.0", 65536L);
    VALID_SUBNET_MASKS.put("255.254.0.0", 131072L);
    VALID_SUBNET_MASKS.put("255.252.0.0", 262144L);
    VALID_SUBNET_MASKS.put("255.248.0.0", 524288L);
    VALID_SUBNET_MASKS.put("255.240.0.0", 1048576L);
    VALID_SUBNET_MASKS.put("255.224.0.0", 2097152L);
    VALID_SUBNET_MASKS.put("255.192.0.0", 4194304L);
    VALID_SUBNET_MASKS.put("255.128.0.0", 8388608L);
    VALID_SUBNET_MASKS.put("255.0.0.0", 16777216L);
    VALID_SUBNET_MASKS.put("254.0.0.0", 33554432L);
    VALID_SUBNET_MASKS.put("252.0.0.0", 67108864L);
    VALID_SUBNET_MASKS.put("248.0.0.0", 134217728L);
    VALID_SUBNET_MASKS.put("240.0.0.0", 268435456L);
    VALID_SUBNET_MASKS.put("224.0.0.0", 536870912L);
    VALID_SUBNET_MASKS.put("192.0.0.0", 1073741824L);
    VALID_SUBNET_MASKS.put("128.0.0.0", 2147483648L);
    VALID_SUBNET_MASKS.put("0.0.0.0", 4294967296L);
  }

  /**
   * Return if a hostname is the local machine
   *
   * @param hostname
   *     hostname
   */
  public static boolean isLocalAddress(String hostname) {
    if (hostname == null) {
      return false;
    }
    try {
      final InetAddress addr = InetAddress.getByName(hostname);
      // Check if the address is either a valid local, loop back or on any interface.
      return addr.isAnyLocalAddress() || addr.isLoopbackAddress() || NetworkInterface.getByInetAddress(addr) != null;
    } catch (UnknownHostException | SocketException e) {
      logger.warn("Exception when checking if the hostname - {} is a local address - {}", hostname, e.getMessage(), e);
      return false;
    }
  }

  /**
   * Return if IP is the loop back address
   *
   * @param ip
   *     IP
   */
  public static boolean isLoopbackAddress(String ip) {
    if (ip == null) {
      return false;
    }
    try {
      final InetAddress addr = InetAddress.getByName(ip);
      return addr.isLoopbackAddress();
    } catch (UnknownHostException e) {
      logger.warn("Exception when checking if the ip - {} is a loop back address.", ip, e.getMessage(), e);
      return false;
    }
  }

  /**
   * Check if the given IP address is a valid IP address.
   *
   * @param ipAddress
   *     the IP address to check
   * @return if the IP address is a valid IP address
   */
  public static boolean isValidIp(String ipAddress) {
    if (null == ipAddress) {
      return false;
    }
    return isValidInet4Address(ipAddress) || isValidInet6Address(ipAddress);
  }

  /**
   * Check if the given IP address is a valid IPv6 address.
   *
   * @param ipAddress
   *     the IP address to check
   * @return if the IP address is a valid IPv6 address
   */
  private static boolean isValidInet6Address(String ipAddress) {
    boolean containsCompressedZeroes = ipAddress.indexOf("::") > -1;
    if (containsCompressedZeroes && ipAddress.indexOf("::") != ipAddress.lastIndexOf("::")) {
      return false;
    } else if (ipAddress.startsWith(":") && !ipAddress.startsWith("::") || ipAddress.endsWith(":") && !ipAddress.endsWith("::")) {
      return false;
    } else {
      String[] octets = ipAddress.split(":");
      if (containsCompressedZeroes) {
        List<String> octetList = CollectionUtil.array2List(octets);
        if (ipAddress.endsWith("::")) {
          octetList.add("");
        } else if (ipAddress.startsWith("::") && !octetList.isEmpty()) {
          octetList.remove(0);
        }
        octets = octetList.toArray(octets);
      }

      if (octets.length > 8) {
        return false;
      } else {
        int validOctets = 0;
        int emptyOctets = 0;

        for (int index = 0; index < octets.length; ++index) {
          String octet = octets[index];
          if (octet.length() == 0) {
            ++emptyOctets;
            if (emptyOctets > 1) {
              return false;
            }
          } else {
            emptyOctets = 0;
            if (octet.indexOf(".") > -1) {
              if (!ipAddress.endsWith(octet)) {
                return false;
              }

              if (index <= octets.length - 1 && index <= 6) {
                if (!isValidInet4Address(octet)) {
                  return false;
                }

                validOctets += 2;
                continue;
              }

              return false;
            }

            if (octet.length() > 4) {
              return false;
            }

            int octetInt;
            try {
              octetInt = Integer.valueOf(octet, 16);
            } catch (NumberFormatException var10) {
              return false;
            }

            if (octetInt < 0 || octetInt > 65535) {
              return false;
            }
          }

          ++validOctets;
        }

        if (validOctets < 8 && !containsCompressedZeroes) {
          return false;
        } else {
          return true;
        }
      }
    }
  }

  /**
   * Check if the given IP address is a valid IPv4 address.
   *
   * @param ipAddress
   *     the IP address to check
   * @return if the IP address is a valid IPv4 address
   */
  public static boolean isValidInet4Address(String ipAddress) {
    String[] groups = RegexUtil.getAllGroups(ipAddress, IPV4_PATTERN);
    if (groups == null) {
      return false;
    } else {
      for (int i = 0; i <= 3; ++i) {
        String ipSegment = groups[i];
        if (ipSegment == null || ipSegment.length() == 0) {
          return false;
        }
        int iIpSegment;
        try {
          iIpSegment = Integer.parseInt(ipSegment);
        } catch (NumberFormatException e) {
          return false;
        }
        if (iIpSegment > 255 || iIpSegment < 0) {
          return false;
        }
        if (String.valueOf(iIpSegment).length() != ipSegment.length()) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * Get the increase IP address based on the given IP address and increase amount.
   *
   * @param ipAddress
   *     the start IP address
   * @param amount
   *     the increase amount
   * @return the increased IP address
   */
  private static String incIpAddress(String ipAddress, int amount) {
    if (!isValidIp(ipAddress)) {
      throw new IllegalArgumentException("Invalid IP : " + ipAddress);
    }
    int incrementedIp = toIntegerIp(ipAddress) + amount;
    return toStringIp(incrementedIp);
  }

  /**
   * Convert a textual IP to an integer.
   */
  public static int toIntegerIp(String ip) {
    if (StringUtil.isBlank(ip)) {
      return 0;
    }
    String[] parts = ip.split(RegexConstants.REGEX_DOT);
    return (Integer.parseInt(parts[0]) << 24 | Integer.parseInt(parts[1]) << 16 | Integer.parseInt(parts[2]) << 8 | Integer.parseInt(parts[3]));
  }

  /**
   * Convert a integer IP to an textual.
   */
  public static String toStringIp(int ip) {
    return String.format("%d.%d.%d.%d", ip >>> 24 & 0xFF, ip >> 16 & 0xFF, ip >> 8 & 0xFF, ip & 0xFF);
  }

  /**
   * Check if a String is a valid subnet mask.
   *
   * @param s
   *     the String to check
   * @return if a String is a valid subnet mask
   */
  public static boolean isValidSubnet(String s) {
    return VALID_SUBNET_MASKS.containsKey(s);
  }

  /**
   * Check if the IPs in the subnet.
   *
   * @param subnetMask
   *     the subnet mask
   * @param ips
   *     the IPs to check
   */
  public static boolean isIpListInSubnet(String subnetMask, String... ips) {
    Assert.notNull(ips);
    Assert.notNull(subnetMask);
    int n = ips.length;
    if (n == 0) {
      logger.warn("Checking empty Ip list in a subnet.");
      return false;
    }
    try {
      byte[] base = InetAddress.getByName(ips[0]).getAddress();
      byte[] mask = InetAddress.getByName(subnetMask).getAddress();

      if (base.length != mask.length) {
        return false;
      }
      for (int i = 1; i < n; i++) {
        byte[] candidates = InetAddress.getByName(ips[i]).getAddress();
        if (candidates.length != mask.length) {
          return false;
        }

        for (int j = 0; j < mask.length; j++) {
          if ((base[j] & mask[j]) != (candidates[j] & mask[j])) {
            return false;
          }
        }
      }
    } catch (UnknownHostException e) {
      logger.error("One of IP address could not be determined. netmask: {}, ips: {}", subnetMask, ips, e);
      return false;
    }
    return true;
  }

  /**
   * Check for a valid hostname.
   */
  public static boolean isValidHostname(String s) {
    return s != null && s.length() <= 64 && HOSTNAME_PATTERN.matcher(s).matches();
  }

  /**
   * Check for a valid vlanId.
   */
  public static boolean isValidVlanId(int vlanId) {
    return vlanId >= 0 && vlanId <= 4094;
  }

  /**
   * Get the network prefix length corresponding to the specified subnet mask.
   */
  public static int getPrefixLengthFromSubnet(String subnet) {
    long maxLength = VALID_SUBNET_MASKS.get(subnet);
    maxLength >>= 1;

    int oneCount = 0;
    while (maxLength > 0) {
      maxLength >>= 1;
      oneCount++;
    }
    return 32 - oneCount;
  }

  /**
   * @param host
   *     the host
   * @param port
   *     the port to check
   * @return if the port is on
   * @see #isPortOn(String, int, int) , with default 10 seconds timeout.
   */
  public static boolean isPortOn(String host, int port) {
    return isPortOn(host, port, 10_000);
  }

  /**
   * Check if the specified host and port is available to access
   *
   * @param host
   *     the host
   * @param port
   *     the port to check
   * @param timeout
   *     in milliseconds
   * @return if the port is on
   */
  public static boolean isPortOn(String host, int port, int timeout) {
    if (!isValidPort(port)) {
      throw new IllegalArgumentException(String.format("provided port [%d] is invalid.", port));
    }
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress(host, port), timeout);
      return true;
    } catch (Exception e) {
      logger.info("The port is not availble: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Check if the port is valid.
   *
   * @param port
   *     the port to check
   * @return if the port is valid
   */
  public static boolean isValidPort(int port) {
    return port > 0 && port <= MAX_PORT;
  }

  /**
   * Resolve the hostname to IP address.
   *
   * @param hostname
   *     the host name to resolve
   * @return the resolved IP address
   * @throws UnknownHostException
   *     if the hostname is unknown or unresolvable
   * @throws IllegalArgumentException
   *     if the hostname is invalid
   */
  public static String resolveHostname2IpAddress(String hostname) throws UnknownHostException, IllegalArgumentException {
    if (!isValidHostname(hostname)) {
      throw new IllegalArgumentException(String.format("invalid hostname [%s].", hostname));
    } else {
      InetAddress address = InetAddress.getByName(hostname);
      return convert2StingIpAddress(address.getAddress());
    }
  }

  /**
   * Convert IP address in byte array to String.
   *
   * @param bytes
   *     the IP address in byte array
   * @return convert IP address
   * @throws IllegalArgumentException
   *     if the IP address in byte array is invalid
   */
  public static String convert2StingIpAddress(byte[] bytes) throws IllegalArgumentException {
    if (null == bytes || bytes.length != 4) {
      throw new IllegalArgumentException("invalid IP address");
    }
    StringJoiner joiner = new StringJoiner(".");
    for (byte b : bytes) {
      joiner.add(String.valueOf(b & 0xFF));
    }
    return joiner.toString();
  }
}
