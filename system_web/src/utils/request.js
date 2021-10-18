import axios from "axios";
import Element from 'element-ui'
import {getToken} from "./auth";
import {logout} from "@/utils/login";

let request = axios.create()  //ES6语法，定义局部变量。创建axios实例

//添加响应拦截器，用request代替axios，使所有失败的响应报错
//两个参数都必须return
request.interceptors.response.use(response=>{
    console.log(response);//每次响应控制台打印
    return response
},
    error => {
    Element.Message.error('请求失败'+error) //请求失败报错
        let code = error.response.data.status
        if(code===401){//当发生401无权限错误时，执行注销
            logout()
        }
    return Promise.reject(error)
    })

//请求拦截器，在每次请求前加上token，因为后端的非Anonymous的接口需要token权限
request.interceptors.request.use(config=>{
    //console.log('请求拦截!')
    if(getToken()){
        config.headers['Authorization'] = getToken()
    }
        return config
    },
    error => {
        return Promise.reject(error)
    })

export default request
