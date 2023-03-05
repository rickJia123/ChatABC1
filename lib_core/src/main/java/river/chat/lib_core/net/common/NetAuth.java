package river.chat.lib_core.net.common;

/**
 * Create by Circle on 2019/9/24
 */
public class NetAuth {
    public static final String PUB_KEY = "-----BEGIN CERTIFICATE-----\n" +
            "MIIGKzCCBROgAwIBAgIQDZJ/xmerczCuTmJdNOVujzANBgkqhkiG9w0BAQsFADBe\n" +
            "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
            "d3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRSYXBpZFNTTCBSU0EgQ0EgMjAxODAe\n" +
            "Fw0xOTA5MTEwMDAwMDBaFw0yMTA5MTAxMjAwMDBaMBcxFTATBgNVBAMMDCouaW1r\n" +
            "ZWxhLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJ5lSBMXwvMw\n" +
            "lg0V1gL+LWijFcBNIh+gZ5YWnfJRGNPHTPOkE+RqBJ1eGmC8Z8OmNZmBuzgyQAIr\n" +
            "e/kAtwuew3xLc9V3l/tJw8LWWYT5UgtERjJjCtmdhpfTqgtmuJ+WcbAUp+ANHjgt\n" +
            "sr/6Ybc8Hvt8NS8NMo+q7XWlQnbcBEB/5nr3seP7nHER0yQK/t1ct1pukpuLeLv+\n" +
            "qb+wszogMod0/BB2sJFBy7GUI/4hAxF2dqm/lx75bG6ErbWv1UBAaEPfLhs9EeLl\n" +
            "zJzHLYITZQ6iseljJsmy3dAsj7r2oue4TVGq172/+2wzFQ6fALv7JSdLzBp7k9kn\n" +
            "LLbf05eBuVUCAwEAAaOCAyowggMmMB8GA1UdIwQYMBaAFFPKF1n8a8ADIS8aruSq\n" +
            "qByCVtp1MB0GA1UdDgQWBBSDKLtx3x2dUq60rpK0qO06i5g3tjAjBgNVHREEHDAa\n" +
            "ggwqLmlta2VsYS5jb22CCmlta2VsYS5jb20wDgYDVR0PAQH/BAQDAgWgMB0GA1Ud\n" +
            "JQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjA+BgNVHR8ENzA1MDOgMaAvhi1odHRw\n" +
            "Oi8vY2RwLnJhcGlkc3NsLmNvbS9SYXBpZFNTTFJTQUNBMjAxOC5jcmwwTAYDVR0g\n" +
            "BEUwQzA3BglghkgBhv1sAQIwKjAoBggrBgEFBQcCARYcaHR0cHM6Ly93d3cuZGln\n" +
            "aWNlcnQuY29tL0NQUzAIBgZngQwBAgEwdQYIKwYBBQUHAQEEaTBnMCYGCCsGAQUF\n" +
            "BzABhhpodHRwOi8vc3RhdHVzLnJhcGlkc3NsLmNvbTA9BggrBgEFBQcwAoYxaHR0\n" +
            "cDovL2NhY2VydHMucmFwaWRzc2wuY29tL1JhcGlkU1NMUlNBQ0EyMDE4LmNydDAJ\n" +
            "BgNVHRMEAjAAMIIBfgYKKwYBBAHWeQIEAgSCAW4EggFqAWgAdgCkuQmQtBhYFIe7\n" +
            "E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAW0eL9USAAAEAwBHMEUCIGYvVAWSd1ep\n" +
            "OsJMAjfREZXUQMwtoTX3htd9qeBjJU99AiEA2wioxJOKh+QUTbGwbf7YsfhYQHmD\n" +
            "2N13wLKmlvRceo4AdQCHdb/nWXz4jEOZX73zbv9WjUdWNv9KtWDBtOr/XqCDDwAA\n" +
            "AW0eL9WGAAAEAwBGMEQCIF4xPbmmnziIcmn8tWWCXTSw7cn5yk32mC9AKCjMC7Fa\n" +
            "AiBwG5QczuWDbxjO188EM7g+IIIEIf40aWj6/+ipwaWQ5AB3AESUZS6w7s6vxEAH\n" +
            "2Kj+KMDa5oK+2MsxtT/TM5a1toGoAAABbR4v1IwAAAQDAEgwRgIhAM2Z09A9iFxB\n" +
            "LVoL66MckQEpTPiPFEWRd61EJq+KGWliAiEAu0cCOZWyk8Qvk8Q1xzgS21RZeaBo\n" +
            "VHMHmM5yZHHKpoYwDQYJKoZIhvcNAQELBQADggEBANcYUy+odTcLQAWVt/B6KvG3\n" +
            "35sYCmE4yBaruFIDRQni4Wq6YXNapqqs/qQzbDPc+TbfoaImU8HzfUxbSkaT3d1t\n" +
            "b2b+nAyQxJN/VzVwXm8JRjAnD3h1GTfyyIgF/69ET6Y++HOjwwFVnZqUi/+J4VdP\n" +
            "NIPUOsqmC3gZ6U+0hVFakvAwhAJ6s4BHkVCTAK3xERhVylELX9ncoPi6VL36DBUk\n" +
            "YXz7iWWmTsdAMWdBcZQQmKf69FuScVOjeHmk81O+0XsVw6ebZBB9RjNVinTC4aGk\n" +
            "wWAjijHgkEm0f4dt6Mw5l7Tz/eSboHxOw6PH6/nyKTeFqsz0lJACE0RcbB4ld9k=\n" +
            "-----END CERTIFICATE-----\n" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIEsTCCA5mgAwIBAgIQCKWiRs1LXIyD1wK0u6tTSTANBgkqhkiG9w0BAQsFADBh\n" +
            "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
            "d3cuZGlnaWNlcnQuY29tMSAwHgYDVQQDExdEaWdpQ2VydCBHbG9iYWwgUm9vdCBD\n" +
            "QTAeFw0xNzExMDYxMjIzMzNaFw0yNzExMDYxMjIzMzNaMF4xCzAJBgNVBAYTAlVT\n" +
            "MRUwEwYDVQQKEwxEaWdpQ2VydCBJbmMxGTAXBgNVBAsTEHd3dy5kaWdpY2VydC5j\n" +
            "b20xHTAbBgNVBAMTFFJhcGlkU1NMIFJTQSBDQSAyMDE4MIIBIjANBgkqhkiG9w0B\n" +
            "AQEFAAOCAQ8AMIIBCgKCAQEA5S2oihEo9nnpezoziDtx4WWLLCll/e0t1EYemE5n\n" +
            "+MgP5viaHLy+VpHP+ndX5D18INIuuAV8wFq26KF5U0WNIZiQp6mLtIWjUeWDPA28\n" +
            "OeyhTlj9TLk2beytbtFU6ypbpWUltmvY5V8ngspC7nFRNCjpfnDED2kRyJzO8yoK\n" +
            "MFz4J4JE8N7NA1uJwUEFMUvHLs0scLoPZkKcewIRm1RV2AxmFQxJkdf7YN9Pckki\n" +
            "f2Xgm3b48BZn0zf0qXsSeGu84ua9gwzjzI7tbTBjayTpT+/XpWuBVv6fvarI6bik\n" +
            "KB859OSGQuw73XXgeuFwEPHTIRoUtkzu3/EQ+LtwznkkdQIDAQABo4IBZjCCAWIw\n" +
            "HQYDVR0OBBYEFFPKF1n8a8ADIS8aruSqqByCVtp1MB8GA1UdIwQYMBaAFAPeUDVW\n" +
            "0Uy7ZvCj4hsbw5eyPdFVMA4GA1UdDwEB/wQEAwIBhjAdBgNVHSUEFjAUBggrBgEF\n" +
            "BQcDAQYIKwYBBQUHAwIwEgYDVR0TAQH/BAgwBgEB/wIBADA0BggrBgEFBQcBAQQo\n" +
            "MCYwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmRpZ2ljZXJ0LmNvbTBCBgNVHR8E\n" +
            "OzA5MDegNaAzhjFodHRwOi8vY3JsMy5kaWdpY2VydC5jb20vRGlnaUNlcnRHbG9i\n" +
            "YWxSb290Q0EuY3JsMGMGA1UdIARcMFowNwYJYIZIAYb9bAECMCowKAYIKwYBBQUH\n" +
            "AgEWHGh0dHBzOi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCwYJYIZIAYb9bAEBMAgG\n" +
            "BmeBDAECATAIBgZngQwBAgIwDQYJKoZIhvcNAQELBQADggEBAH4jx/LKNW5ZklFc\n" +
            "YWs8Ejbm0nyzKeZC2KOVYR7P8gevKyslWm4Xo4BSzKr235FsJ4aFt6yAiv1eY0tZ\n" +
            "/ZN18bOGSGStoEc/JE4ocIzr8P5Mg11kRYHbmgYnr1Rxeki5mSeb39DGxTpJD4kG\n" +
            "hs5lXNoo4conUiiJwKaqH7vh2baryd8pMISag83JUqyVGc2tWPpO0329/CWq2kry\n" +
            "qv66OSMjwulUz0dXf4OHQasR7CNfIr+4KScc6ABlQ5RDF86PGeE6kdwSQkFiB/cQ\n" +
            "ysNyq0jEDQTkfa2pjmuWtMCNbBnhFXBYejfubIhaUbEv2FOQB3dCav+FPg5eEveX\n" +
            "TVyMnGo=\n" +
            "-----END CERTIFICATE-----\n";
}
