import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJUW/7bhcg2Rfzm+gzE8/KrpS3\n' +
    'EOhQpCqFVh7rfpHV6Lx5hsx4r826lzMbY+JsNdvdh5slgUmuJydtF7eec1dRbWkk\n' +
    'N7GjMCZlPMzxZDgLYmYn24l+F/3OKx3L50oYKjD/QbfymbizM0irAXb433kVF9l/\n' +
    '9c1nYmdcJVA6hEKavQIDAQAB'

//
// 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZqmpLKGrdcivhB+ZFUVhdsQ29jqfHcATPfY8Ba\n' +
//     'iQeMX9PzcDqE1CDKblrVj8i2qAdpjA37fXPCxzakZDuhbZBW4XKtRZgWsDeZlRzEYMZgNl3A3L+uh\n' +
//     'v7UkSxuphc0E+iS6gaSSF1uq+PZ2R8OBebZ+GXcUSM+udHg5mvijHDowIDAQAB'


const privateKey = 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKgkGSDng3JCXRHG1ocMoW1n94pzxkp4d7lv5klcSP8cU05LcYhQ8WJ9c6XoG3EOyyee8Pmm26pxXzHwAoF/TSI/55rIdORlMiLgojF6e4noHocqdXAvcyAYvuh/ODSqXtCoyIUzvyYzmwWeBD/FT3CpAONZ5LeZy63Ae72LfZadAgMBAAECgYAZv41pkIp8kNUXx89KVxDRZD5wIVKYTOJor04gpdU32+qV7s+wBFUys+tl2Kl7IrzRkHFuibLR4xKk3xBDLG9vM5eW5VLIM90pS1CUt8UaBVjls5lHfOmnlMb9GINOCsYe0zfzcJ9CI+d6Xx3u/5TbuN7XntSIu1KgiAwiDYPBMQJBANzIuewLL1sJbmgqOtP7EiO9xlWgstX+gAfOiOUURvbueQ4x0F58GYisNF1pNUmcwLqPgYezUGvGIJaqZILWiq8CQQDC9cw6I14kctpqIt4NM8utcrw6LOtPbV5Lis2bJoQlZ7sNM971Ozl7PgnqlZRKa3x2fTST8vhgPPV5fQPlgdZzAkEAozXjcyHllPfI3UnyiQf+JdvBRUVxTo4/mQXN3wJ5X0t0w9CBR4eX3JfocfE3Sj6Nnpw9VdJxreod5yL5AFuW0wJAH2VqACrV7FS6q8D1tXaT5FjHyy6UrnPUbaHqvoOTYFq2GsgxrZheB2TUP/zapqlsn8BGCVVlfkoS1f+18u3X1QJBAL5J5XzFtmpsqbWmT8BcLcV7tBJgZbQDg4na+2kGMspzJq6Y75/7MrXi0G/KXTKwDadOVYSnG+qXwRMqnR5KG50='

//
// 'MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0vfvyTdGJkdbHkB8\n' +
//   'mp0f3FE0GYP3AYPaJF7jUd1M0XxFSE2ceK3k2kw20YvQ09NJKk+OMjWQl9WitG9p\n' +
//   'B6tSCQIDAQABAkA2SimBrWC2/wvauBuYqjCFwLvYiRYqZKThUS3MZlebXJiLB+Ue\n' +
//   '/gUifAAKIg1avttUZsHBHrop4qfJCwAI0+YRAiEA+W3NK/RaXtnRqmoUUkb59zsZ\n' +
//   'UBLpvZgQPfj1MhyHDz0CIQDYhsAhPJ3mgS64NbUZmGWuuNKp5coY2GIj/zYDMJp6\n' +
//   'vQIgUueLFXv/eZ1ekgz2Oi67MNCk5jeTF2BurZqNLR3MSmUCIFT3Q6uHMtsB9Eha\n' +
//   '4u7hS31tj1UWE+D+ADzp59MGnoftAiBeHT7gDMuqeJHPL4b+kC+gzV4FGTfhR9q3\n' +
//   'tTbklZkD2A=='

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对需要加密的数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey)
  return encryptor.decrypt(txt)
}

