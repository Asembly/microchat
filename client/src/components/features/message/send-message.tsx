'use client'

import client, { publish } from "@/utils/stomp"
import { useStore } from "@/utils/store"
import { FieldsetLegend, Flex, IconButton, Input } from "@chakra-ui/react"
import { useSession } from "next-auth/react"
import { useState } from "react"

export default function SendButton()
{
    
    const {selectedChat} = useStore()
    const [text, setText] = useState("")
    const { data: session, status } = useSession();
    const token = session?.access?.token

    const handleKeyPress = (event: any) => {
            if (event.key === 'Enter') {
            event.preventDefault(); 
            sendMessage();
        }
    }
    const sendMessage = () => {
        if(client && client.connected)
        {
            console.log("Sending message...")
            if(token)
            {
                publish(`/app/chat/sendMessage/${selectedChat.id}`,
                    JSON.stringify({text: text, author_id: session?.user.id, chat_id: selectedChat.id}), token )
            }
            setText("")
        }
    }

    return(
        <Flex
      w="100%"
      align="center"
      px={2}
      py={1}
      gap={2}
    >
      <Input
        onKeyPress={handleKeyPress}
        value={text}
        onChange={e => setText(e.target.value)}
        bg="blue.50"
        color="msgText"
        border="none"
        placeholder={`написать в ${selectedChat.title}`}
        _placeholder={{ color: "gray.500" }}
        h="32px"
        fontSize="sm"
        flex="1"
        rounded="md"
        focusRingColor="transparent"
        _focus={{ boxShadow: "none" }}
        _active={{ boxShadow: "none" }}
      />
      <IconButton
        onClick={sendMessage}
        aria-label="Создать чат"
        bg="blue.50"
        color="blue.400"
        _hover={{ bg: "blue.200", color: "blue.700" }}
        rounded="md"
        size="sm"
      />
    </Flex>
    )
}