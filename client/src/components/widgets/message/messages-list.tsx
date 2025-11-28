'use client'

import { useStore } from "@/utils/store"
import MessageRemoveButton from "../../features/message/remove-btn"
import { Avatar, Box, Flex, IconButton, Text } from "@chakra-ui/react"
import { useSession } from "next-auth/react"
import { useEffect, useRef } from "react"
import MessageElement from "./message-element"

export default function MessagesList() {

    const {messages, selectedChat, loadMessages, hasMore} = useStore()

    const bottomRef:any = useRef(null);
    const messagesContainerRef = useRef(null);

    useEffect(() => {
      const container = messagesContainerRef.current as any;
      // bottomRef.current.scrollIntoView({ behavior: "smooth"});
      if (!container) return;

      const handleScroll = () => {
        if (container.scrollTop === 0 && hasMore) {
          console.log(messages[0])
          loadMessages(selectedChat.id, messages[0]?.created_at?.toString());
        }
      };

      container.addEventListener('scroll', handleScroll);
      return () => container.removeEventListener('scroll', handleScroll);
    }, [messages, loadMessages, selectedChat]);

    return (
      <Box ref={messagesContainerRef} xlDown={{w:"full"}} overflowY={"auto"} lg={{w:"70%"}} h={"100%"}>
        <Flex alignItems={"center"}
        direction="column" gap={5}>
          {
            selectedChat.id == undefined 
            ?
              <Box>
                Выберите чат, чтобы начать общение
              </Box>
            :
                messages.length == 0 
                ? 
                <Box>
                  Чат пустой, напишите что нибудь 
                </Box>
                :
                messages.map((msg, i) => (
                  <MessageElement key={i} msg={msg}/>
                ))
          }
        </Flex>
        {/* <div ref={bottomRef} /> */}
    </Box>
    )
  }