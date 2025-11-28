'use server'
import { revalidatePath, revalidateTag } from "next/cache";
import { serverInstance } from "./config"
import { AxiosError, isAxiosError } from "axios";
import { redirect } from "next/navigation";
import { auth, signOut } from "./auth";
import { Session } from "next-auth";
import { Timestamp } from "next/dist/server/lib/cache-handlers/types";

export async function getUsers()
{
    const session: Session | null = await auth()  
    const token = session?.access?.token
    
    const users: User[] = await serverInstance.get(`/users`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .then(res => res.data)
    .catch(error => error)

    console.log("Get Users: " + users)

    return users
}

export async function getMessagesByChatId(chat_id: string, beforeDate?: string | null)
{
    const session: Session | null = await auth()  
    const token = session?.access?.token

    console.log("Chat Id" + chat_id)

    const messages: MessageLazy = await serverInstance.get(`/messages/chat/${chat_id}?beforeDate=${beforeDate || ''}`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .then(res => res.data)
    .catch(error => error)

    console.log("TEST " + messages)

    return messages
}

export async function getUsersByChatId(chat_id: string)
{

    const session: Session | null = await auth()  
    const token = session?.access?.token

    console.log("User chat_id" + chat_id)

    const users: User[] = await serverInstance.get(`/users/chat/${chat_id}`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .then(res => res.data)
    .catch(error => error)

    console.log(users + "Пользователи чата")

    if(users.length == null)
        return []

    return users 
}

export async function getChatsByUserId(user_id: string)
{

    const session: Session | null = await auth()  
    const token = session?.access?.token

    console.log("Chat user_id" + user_id)

    const chats: Chat[] = await serverInstance.get(`/chats/user/${user_id}`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .then(res => res.data)
    .catch(error => error)

    console.log("HELLOOjoo " + chats)

    if(chats.length == null)
        return []

    return chats
}

export async function deleteMessage(msg_id: string)
{
    const session: Session | null = await auth()  
    const token = session?.access?.token

    await serverInstance.delete(`/messages/${msg_id}`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .catch(error => error)
}

export async function deleteChat(chat_id: string)
{
    const session: Session | null = await auth()  
    const token = session?.access?.token

    await serverInstance.delete(`/chats/${chat_id}`,
        {
            headers: {
                'Authorization': 'Bearer ' + token 
            },
        }
    )
    .catch(error => error)
}

export async function createChat(prevState: Chat, formData: FormData)
{
    const session: Session | null = await auth()  
    const token = session?.access?.token

    const usersIdRaw = formData.get("users_id")?.toString() || "[]";

    const usersIdArray = JSON.parse(usersIdRaw);

    const data = {
    title: formData.get("title"), 
    users_id: usersIdArray  
    };

    console.log("Added users: ", usersIdArray);
    console.log("data: ", JSON.stringify(data));

    const response: Chat = await serverInstance.post(`/chats`, data,
        {
            headers: {
                'Authorization': "Bearer " + token,
            }
        }
    )
    .then(res => res.data)
    .catch(error => error)

    return response
}

export async function addUserToChat(prevState: Chat, formData: FormData)
{
    const session: Session | null = await auth()  
    const token = session?.access?.token

    const usersIdRaw = formData.get("users_id")?.toString() || "[]";

    const usersIdArray = JSON.parse(usersIdRaw);

    const data = {
        users_id: usersIdArray  
    };

    const chat_id = formData.get("chat_id")

    console.log(chat_id + "HEGKFDJ:JSKLGJ:KLDJKL:GJDSKL")

    console.log("Added users: ", usersIdArray);
    console.log("data: ", JSON.stringify(data));

    const response: Chat = await serverInstance.post(`/chats/${chat_id}/add`, data,
        {
            headers: {
                'Authorization': "Bearer " + token 
            }
        }
    )
    .then(res => res.data)
    .catch(error => error)

    revalidateTag('/', 'max')

    return response
}

export async function signUp(data: SignUp)
{
   console.log(data)

   const response: User | AxiosError = await serverInstance.post(`/auth/sign-up`, data,
      {
         headers: {
            'Content-Type': "application/json"
         }
      }
   )  
   .then(res => res.data)
   .catch(error => error)

   if(!isAxiosError(response))
   {
      console.log("Пользователь успешно зарегистрировался. " + response)
      redirect("/sign-in")
   }

   return response
}

/* /api/auth/sign-in POST */
export async function login(data: Login)
{


    const response: AxiosError | ResponseLogin = await serverInstance.post(`/auth/sign-in`,data,
        {
            headers: {
                'Content-Type': "application/json"
            },
        }
    )  
    .then(res => res.data)
    .catch(error => error);

    if(isAxiosError(response))
    {
        console.log('Пользователь с такими данными не найден.')
        return response 
    }

    console.log("Пользователь успешно авторизовался на сервере: " + response.username)

    return response 
}

/* /api/auth/logout POST */
export async function logout(refreshToken: string | undefined)
{
    console.log(refreshToken)

    if(refreshToken == undefined)
        return "Refresh Token is undefined"

    const session: Session | null = await auth()  
    const token = session?.access?.token

    const data = {
        refreshToken: refreshToken
    } 

    const response = await serverInstance.post(`/auth/logout`,data,
        {
            headers: {
                'Authorization': "Bearer " + token,
                'Content-Type': "application/json"
            },
        }
    )  
    .then(res => res.data)
    .catch(error => error)

    await signOut()   

    return response 
}

export async function refresh(refreshToken: string)
{
   const response: AccessToken = await serverInstance.post(`/auth/refresh/${refreshToken}`,
      {
         headers: {
            'Content-Type': "application/json"
         },
      }
   )  
   .then(res => res.data)
   .catch(error => error)

   console.log(response)

   if(!response)
    signOut()

   console.log("Токен обновился на сервере: " + response.token)

   return response
}