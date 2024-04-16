package river.chat.lib_core.net.common;

/**
 * Create by Circle on 2019/9/24
 * 用于https的公钥
 */
public class NetAuth {
    public static final String PUB_KEY = "-----BEGIN CERTIFICATE-----\n" +
            "MIIGZjCCBM6gAwIBAgIQM/aZ5LGox04G37pbWPtCRDANBgkqhkiG9w0BAQwFADBZ\n" +
            "MQswCQYDVQQGEwJDTjElMCMGA1UEChMcVHJ1c3RBc2lhIFRlY2hub2xvZ2llcywg\n" +
            "SW5jLjEjMCEGA1UEAxMaVHJ1c3RBc2lhIFJTQSBEViBUTFMgQ0EgRzIwHhcNMjMw\n" +
            "OTEzMDAwMDAwWhcNMjQwOTEyMjM1OTU5WjASMRAwDgYDVQQDEwdibGp5LmNjMIIB\n" +
            "IjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzr3Yi+PYh/1eawKmAFOD7GTx\n" +
            "FjjlPOlnqKU/t0fYxINKhS+3UW5a6Obo74KKxCFHQ1wZ73Ga6Wckh9dQ+XRkKT/F\n" +
            "yvUCF3yMwXHvowg4JwB2JcN1zP8DeQuNXHEfAmnN+av0ZaEUEpLqJSawmepfnmL5\n" +
            "Nke94KOt6md5+oh9MZ0drcq28aceNwuCv3bXtPsJhiTjjMXEjIR+Cq8sl4qOQlBA\n" +
            "radyoaHIj9hzVn2N7UiKEPr/nyHFVpUsL9qhDKZa+F9AVgLRzY21ETx2XDUytRT9\n" +
            "2AldjueNzGX/G9UVyjtUGpRzf8CRs7QAIn0X374oxtHmE/6FoU2ScH8bkz/ctwID\n" +
            "AQABo4IC7zCCAuswHwYDVR0jBBgwFoAUXzp8ERB+DGdxYdyLo7UAA2f1VxwwHQYD\n" +
            "VR0OBBYEFAQJAijMgFw8fMW/44P43n5EyiaAMA4GA1UdDwEB/wQEAwIFoDAMBgNV\n" +
            "HRMBAf8EAjAAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjBJBgNVHSAE\n" +
            "QjBAMDQGCysGAQQBsjEBAgIxMCUwIwYIKwYBBQUHAgEWF2h0dHBzOi8vc2VjdGln\n" +
            "by5jb20vQ1BTMAgGBmeBDAECATB9BggrBgEFBQcBAQRxMG8wQgYIKwYBBQUHMAKG\n" +
            "Nmh0dHA6Ly9jcnQudHJ1c3QtcHJvdmlkZXIuY24vVHJ1c3RBc2lhUlNBRFZUTFND\n" +
            "QUcyLmNydDApBggrBgEFBQcwAYYdaHR0cDovL29jc3AudHJ1c3QtcHJvdmlkZXIu\n" +
            "Y24wHwYDVR0RBBgwFoIHYmxqeS5jY4ILd3d3LmJsankuY2MwggF/BgorBgEEAdZ5\n" +
            "AgQCBIIBbwSCAWsBaQB2AHb/iD8KtvuVUcJhzPWHujS0pM27KdxoQgqf5mdMWjp0\n" +
            "AAABio4gyzgAAAQDAEcwRQIgNMEmQV6zzNL5cyES2v12eVscWFmEmja4PF6MEkk1\n" +
            "W6MCIQD0t23iePQjumJAfy0/BM4IuBKlTBUK2ERM/dJqVHokMQB2ANq2v2s/tbYi\n" +
            "n5vCu1xr6HCRcWy7UYSFNL2kPTBI1/urAAABio4gy5QAAAQDAEcwRQIhAPK1G4jd\n" +
            "NnyST6JGDNiGvH/3zAS8XkvcRwg8vl+NQ5zpAiAjQyeh410omclxj3tukytedxrE\n" +
            "f/rHsKJA0RpOtxspvQB3AO7N0GTV2xrOxVy3nbTNE6Iyh0Z8vOzew1FIWUZxH7Wb\n" +
            "AAABio4gy2QAAAQDAEgwRgIhAMKoCg/HGLwcMMZcw65E0zvyr6sSLoo2xKSsytkX\n" +
            "v8T9AiEA9pEj2+466Al4grhg0IdDriilDgI/cC+eQTwAfBuNGkAwDQYJKoZIhvcN\n" +
            "AQEMBQADggGBAJIJ+s8Ag/QnOZf8fJAHU+dLId2MgbckSE6hb8IqZwN1yXB+igID\n" +
            "SiQyNrTcEV0hnrkdJfv5Ds1SMw50hv3AEyDPyTt3DZNptyRG1TyQ9iKkYTKNbQlg\n" +
            "MD3A9j/CRuL6ej9xjR9vjRftuEjFQQlWu8aJTzRKVmUylRFKSP3z90dXyvUY5/Hi\n" +
            "ePzYGx1Pd7tIA/3A28b482AZgCxNux/e+/CnoOBI+UiDcgKik4Bk2XYqTAkz72zc\n" +
            "xFinNaZynvdLdqTaERoKdCR5NtlWZjuIhMzWMibhEdBDRcQn0Ryq3er04Y0VSqwu\n" +
            "ZmgL3Y1vxrYexb0HjgteHVb1wlNVdEbuHnlXggVi4WX+JBA7n28Z6PLBCCBzPjZu\n" +
            "2D4LFVjMKqgFgTf4R03VfCcWHiBDkZWMsNVZFhWE0Yl+PKvYsLIFIml4Jzu+qOrg\n" +
            "2rGtHxC7kWbydl6CY5nDQZ3M7Kzl4hD/VFAE0dnu0InnSXIEEGXGVfF83HX3zA9w\n" +
            "qg8TZtAYMkK8GQ==\n" +
            "-----END CERTIFICATE-----\n" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIFBzCCA++gAwIBAgIRALIM7VUuMaC/NDp1KHQ76aswDQYJKoZIhvcNAQELBQAw\n" +
            "ezELMAkGA1UEBhMCR0IxGzAZBgNVBAgMEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
            "A1UEBwwHU2FsZm9yZDEaMBgGA1UECgwRQ29tb2RvIENBIExpbWl0ZWQxITAfBgNV\n" +
            "BAMMGEFBQSBDZXJ0aWZpY2F0ZSBTZXJ2aWNlczAeFw0yMjAxMTAwMDAwMDBaFw0y\n" +
            "ODEyMzEyMzU5NTlaMFkxCzAJBgNVBAYTAkNOMSUwIwYDVQQKExxUcnVzdEFzaWEg\n" +
            "VGVjaG5vbG9naWVzLCBJbmMuMSMwIQYDVQQDExpUcnVzdEFzaWEgUlNBIERWIFRM\n" +
            "UyBDQSBHMjCCAaIwDQYJKoZIhvcNAQEBBQADggGPADCCAYoCggGBAKjGDe0GSaBs\n" +
            "Yl/VhMaTM6GhfR1TAt4mrhN8zfAMwEfLZth+N2ie5ULbW8YvSGzhqkDhGgSBlafm\n" +
            "qq05oeESrIJQyz24j7icGeGyIZ/jIChOOvjt4M8EVi3O0Se7E6RAgVYcX+QWVp5c\n" +
            "Sy+l7XrrtL/pDDL9Bngnq/DVfjCzm5ZYUb1PpyvYTP7trsV+yYOCNmmwQvB4yVjf\n" +
            "IIpHC1OcsPBntMUGeH1Eja4D+qJYhGOxX9kpa+2wTCW06L8T6OhkpJWYn5JYiht5\n" +
            "8exjAR7b8Zi3DeG9oZO5o6Qvhl3f8uGU8lK1j9jCUN/18mI/5vZJ76i+hsgdlfZB\n" +
            "Rh5lmAQjD80M9TY+oD4MYUqB5XrigPfFAUwXFGehhlwCVw7y6+5kpbq/NpvM5Ba8\n" +
            "SeQYUUuMA8RXpTtGlrrTPqJryfa55hTuX/ThhX4gcCVkbyujo0CYr+Uuc14IOyNY\n" +
            "1fD0/qORbllbgV41wiy/2ZUWZQUodqHWkjT1CwIMbQOY5jmrSYGBwwIDAQABo4IB\n" +
            "JjCCASIwHwYDVR0jBBgwFoAUoBEKIz6W8Qfs4q8p74Klf9AwpLQwHQYDVR0OBBYE\n" +
            "FF86fBEQfgxncWHci6O1AANn9VccMA4GA1UdDwEB/wQEAwIBhjASBgNVHRMBAf8E\n" +
            "CDAGAQH/AgEAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAiBgNVHSAE\n" +
            "GzAZMA0GCysGAQQBsjEBAgIxMAgGBmeBDAECATBDBgNVHR8EPDA6MDigNqA0hjJo\n" +
            "dHRwOi8vY3JsLmNvbW9kb2NhLmNvbS9BQUFDZXJ0aWZpY2F0ZVNlcnZpY2VzLmNy\n" +
            "bDA0BggrBgEFBQcBAQQoMCYwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmNvbW9k\n" +
            "b2NhLmNvbTANBgkqhkiG9w0BAQsFAAOCAQEAHMUom5cxIje2IiFU7mOCsBr2F6CY\n" +
            "eU5cyfQ/Aep9kAXYUDuWsaT85721JxeXFYkf4D/cgNd9+hxT8ZeDOJrn+ysqR7NO\n" +
            "2K9AdqTdIY2uZPKmvgHOkvH2gQD6jc05eSPOwdY/10IPvmpgUKaGOa/tyygL8Og4\n" +
            "3tYyoHipMMnS4OiYKakDJny0XVuchIP7ZMKiP07Q3FIuSS4omzR77kmc75/6Q9dP\n" +
            "v4wa90UCOn1j6r7WhMmX3eT3Gsdj3WMe9bYD0AFuqa6MDyjIeXq08mVGraXiw73s\n" +
            "Zale8OMckn/BU3O/3aFNLHLfET2H2hT6Wb3nwxjpLIfXmSVcVd8A58XH0g==\n" +
            "-----END CERTIFICATE-----";
}
