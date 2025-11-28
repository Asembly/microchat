type User = {
    id: string,
    username: string,
    chats_id: string[], 
    created_at: Date
}

type Chat = {
    id: string,
    title: string,
    users_id: string[],
    messages_id: string[],
    created_at: Date
}

type Message = {
    id: string,
    text: string,
    author: string,
    chat_id: string,
    created_at: Date
}

type MessageLazy = {
    messages: Message[],
    hasMore: boolean
}

type MessageChatDTO = {
    text: string,
    author_id: string,
    chat_id: string
}

type ResponseLogin = {
    id: string,
    username: string,
    access: AccessToken,
    refresh: RefreshToken,
}

type ResponseRefresh = {
    access_token: string
}

type Login = {
    username: string | unknown,
    password: string | unknown
}

type SignUp = {
    username: string | unknown,
    password: string | unknown
}

type AccessToken = {
    token: string,
    expires_at: number,
}

type RefreshToken = {
    token: string,
    expires_at: number,
}

type MessageError = {
    error: string
}