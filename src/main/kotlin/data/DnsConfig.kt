package data

data class DnsConfig(
    val category: String,
    val name: String,
    val primary: String,
    val secondary: String
)

val sanctionsDns = listOf(
    DnsConfig("Sanctions", "Shecan", "178.22.122.100", "185.51.200.2"),
    DnsConfig("Sanctions", "Electro", "78.157.42.100", "78.157.42.101"),
    DnsConfig("Sanctions", "Begzar", "185.55.226.26", "185.55.226.25"),
    DnsConfig("Sanctions", "Radar", "10.202.10.10", "10.202.10.11"),
    DnsConfig("Sanctions", "OpenDNS", "208.67.222.222", "208.67.220.220"),
    DnsConfig("Sanctions", "403", "10.202.10.202", "10.202.10.102")
)

val speedDns = listOf(
    DnsConfig("Speed", "Yandex", "77.88.8.8", "77.88.8.1"),
    DnsConfig("Speed", "Cloudflare", "1.1.1.1", "1.0.0.1"),
    DnsConfig("Speed", "Google", "8.8.8.8", "8.8.4.4")
)
