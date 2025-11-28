import NextAuth from "next-auth";
import { authConfig } from "./config";
import Credentials from "next-auth/providers/credentials";
import { login, refresh } from "./actions";
import { schemeLogin } from "./schemes";
import { JWT } from "next-auth/jwt";
import { AxiosError, isAxiosError } from "axios";

export const {auth, signIn, signOut, handlers} = NextAuth({
    ...authConfig,
    pages: {
        signIn: "/sign-in"
    },
    callbacks: {
        session: async({session, token}) => {

            if(!token)
                console.log("Проблема с сессией, токен unknown.")    

            session.user.id = token.uid
            session.user.name = token.name
            session.access = token.access
            session.refresh = token.refresh

            return session
        },
        jwt: async({user, token}) => {

            if(user)
            {
                token.name = user.name
                token.access = user.access
                token.refresh = user.refresh
                token.uid = user.id
            }

            console.log(token)
            console.log(Date.now())

            //Проверка действия Refresh токена
            if(token.refresh && Date.now() > token.refresh?.expires_at)
                token.refresh = undefined;

            //Проверка действия Access токена
            if((token.access?.expires_at && Date.now() > token.access.expires_at)  && token.refresh)
            {
                const refreshed = await refresh(token.refresh.token)

                if(!refreshed)
                {
                    console.log("Ошибка, токен не обновился.")
                    token.refresh == undefined
                    return token
                }

                token.access.token = refreshed?.token
                token.access.expires_at = refreshed?.expires_at; 

                if(token.access.token && token.access.expires_at)
                {
                    console.log("Токен обновился в сессии: " + token.access.token)
                    console.log("Новое истечение времени: " + token.access.token)
                }
            }

            if(token && token.access?.token == null)
                return null

           return token 
        },
    },
    session: {
        strategy: 'jwt'
    },
    providers: [Credentials({
        credentials: {
            username:{
                label: 'username',
                placeholder: 'username'
            },
            password:{
                type: 'password',
                label: 'password',
                placeholder: 'password'
            }
        },
        async authorize(credentials, request): Promise<any> {

            const data = ({
                username: credentials.username,
                password: credentials.password
            })

            let response: ResponseLogin | AxiosError = await login(data) 

            if(isAxiosError(response))
                return null

            return{
                id: response.id,
                name: response.username,
                access: {
                    token: response.access.token,
                    expires_at: response.access.expires_at
                },
                refresh: {
                    token: response.refresh.token,
                    expires_at: response.refresh.expires_at
                }
            }
        },
    })]
})