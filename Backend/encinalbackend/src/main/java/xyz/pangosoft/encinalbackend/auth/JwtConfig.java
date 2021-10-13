package xyz.pangosoft.encinalbackend.auth;

public class JwtConfig {

    public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAy3Rlx+F5b0YLidoZ4MWGOZnpqrx4Bqu5AXK0Xodwap/2AoGV\n" +
            "W/soNiefnUrut9Ah3yXQNvJgq9/lfuKLxhr0yjuY+Z/9S9DXIWhKVVJ1akekJJ3u\n" +
            "YXsFc+JSeC+KvkDsqEq1Vec6xLuGPe+lSg8rZvE8LrAE3O9iHAw0Q5AkYuCO0ExC\n" +
            "lmYKaLbigbu15aAzdHeovAM3HzXLolPiMq1oyLRWJoqDiNLC6obrXJBsZDkbodSK\n" +
            "N2UshUydyUgfUWa7NfOTIfzEcpouBnZfzG+HO0SRN8ZVnDmjryyYO023nbkkVoRe\n" +
            "uNfjLvPVHaJGJKDLdmK2laxu9dXowL6T0i+ODwIDAQABAoIBAClmji+WWjeicUaj\n" +
            "8xAiIaYnA72SjHoPB56b0MhLlzybQFxSMip9eTlL5tcDIICXAc1+zFfiCnnvHaws\n" +
            "PXUalyVv6zkgrVJ4NM6/giDKnsn5uhUZbiC3/+wM6es7AWilnYLBcRUB8JuH7VE9\n" +
            "Ks1fON3ByRVXDCZJMBW1Bg2Zj03vNzRWFDi6ad/gpuXjIyi+uz/akG1/ZyuTCl0h\n" +
            "UIZODnkiekyvrKpb4txN6WqoPX5oeNy67ZD+Gcm7Ij5+JIqUhkaixDSnOl4ze6Sg\n" +
            "IaQ/1dkVk261MCHQ0v2iy7sM79MYF/apH3zc5g4fWz13Yo8jEoxeioGykepLcSxB\n" +
            "DkQeOoECgYEA6xgA3BBjBOUavKb5LRJWWLc4t0t0dli6CLqcBlicJ19xQM9YJXlY\n" +
            "3XXPvfM0U9k68JbxCN21A3hJM8hiu1zIz9e8kKZbbB5ggrE99QtJiHBXX0OIa1kM\n" +
            "hgVxnZvuSJBDAeAywRu9EpIC5wRf+70E0+PhNwoI9HyGgs2vpv/lGCkCgYEA3Ywe\n" +
            "ZNtsKqJGes5vbvOAcszMYAIlAfwwtJ6zwefqdt5qyrjUNBjVUsspBBcrGrgHFtM5\n" +
            "TnGTFRcN7x3pySTRMfMA5hVw1f9/DBRXeKUroxgbLeEIphjF0zYinK3y9WzUXhIr\n" +
            "uDhHcpIWX/RaF+inJTFt3Ok36RqbzyZ2E0SdG3cCgYBXBKF800vzieHlpmBhAIKZ\n" +
            "f+gsYG+RRrJFfN03tCDIqhk/hf/4jvwHrQ3iH11lE+kvGI/LWlUgHavq0OdKtsNV\n" +
            "u2L+n7UDRhMvDPAD/dOP/xU4G8UpBuMN0IIHdqoyjcNftF7U/7FyaD1OyV3Fzmc1\n" +
            "DwpT1xENffJEBGJaDCn+UQKBgQCuG+AHAyoOl1ksSe/pEhRwI+K50PWkibz9NXuv\n" +
            "d8Mbh9AFYRvjlWCQoHirbknBxb5MgdMXgspEWMnqkWeZXAXLa13ZdCApDZGavKHB\n" +
            "foy8r8UsWR2i6vUM+A5bc5jsp4p+I2mYbSI5N/W9kxr6jyxMXnwokGAQIYkxUtiY\n" +
            "a+PMIQKBgQCX9/Kg9Y72uUCfHPE2KlWY93N33BxCroA9FilDaLPQ9o7VUKE7Pkt/\n" +
            "3EEueH2ww0WtBilKrNrPttbDotrDjdXvL6mKTjGiugT/qqKza1URyzwY9NdGcDNI\n" +
            "TZrvwQgxQMD2FM8My68QJVvIl5OaF7psQb/OOuy5x1HdUZOyXMWaoQ==\n" +
            "-----END RSA PRIVATE KEY-----";

    public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy3Rlx+F5b0YLidoZ4MWG\n" +
            "OZnpqrx4Bqu5AXK0Xodwap/2AoGVW/soNiefnUrut9Ah3yXQNvJgq9/lfuKLxhr0\n" +
            "yjuY+Z/9S9DXIWhKVVJ1akekJJ3uYXsFc+JSeC+KvkDsqEq1Vec6xLuGPe+lSg8r\n" +
            "ZvE8LrAE3O9iHAw0Q5AkYuCO0ExClmYKaLbigbu15aAzdHeovAM3HzXLolPiMq1o\n" +
            "yLRWJoqDiNLC6obrXJBsZDkbodSKN2UshUydyUgfUWa7NfOTIfzEcpouBnZfzG+H\n" +
            "O0SRN8ZVnDmjryyYO023nbkkVoReuNfjLvPVHaJGJKDLdmK2laxu9dXowL6T0i+O\n" +
            "DwIDAQAB\n" +
            "-----END PUBLIC KEY-----";
}
