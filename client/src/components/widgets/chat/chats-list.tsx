'use client'

import { getChatsByUserId } from "@/utils/actions"
import { Session } from "next-auth"
import { useSession } from "next-auth/react"
import { useEffect, useState } from "react"
import { auth } from "@/utils/auth"
import { Box, Flex } from "@chakra-ui/react"
import ChatElem from "./chat-element"
import ChatElement from "./chat-element"

export default function ChatsList()
{
    const [chats, setChats] = useState<Chat[]>([])
    const { data: session, status } = useSession();

    useEffect(() => {
        if(session?.user?.id)
            getChatsByUserId(session.user.id).then(setChats)
    }, [status, session]) 

    return ( 
        <Flex direction={"column"} w={"100%"} alignItems={"center"}>
           {
            chats.length == 0?
            <Box fontSize={"0.8rem"}>
                Чатов в данный момент нет
            </Box>
            :
            chats.map((item) => (
                    <ChatElement key={item.id} chat={item}/>
            ))
           } 
        </Flex>
    )
}
