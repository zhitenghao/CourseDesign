import Cookies from 'js-cookie'
import Config from '@/settings'

const TokenKey = Config.TokenKey

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token, rememberMe) {
  //console.log(rememberMe)
  if (rememberMe) {
    return Cookies.set(TokenKey, token, { expires: Config.tokenCookieExpires })
  } else return Cookies.set(TokenKey, token)//即使没有选中“记住我”，Token仍被存入cookie中
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
