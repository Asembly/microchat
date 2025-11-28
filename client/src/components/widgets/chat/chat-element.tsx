'use client'
import ChatRemoveButton from "@/components/features/chat/remove-btn";
import { Box, Flex } from "@chakra-ui/react";
import Chat from "./chat";
import { useStore } from "@/utils/store";

export default function ChatElement(props: {chat: Chat})
{
    const {selectedChat, setSelectedChat, fetchMessagesByChatId} = useStore()

    const handler = async() => {
        setSelectedChat(props.chat)
        await fetchMessagesByChatId(props.chat.id) 
    }

    return (
        <Box onClick={handler} borderRadius={"md"} px={"10%"} py={"5%"} w={"full"} _hover={{ bg: "rgba(0, 0, 0, 0.05)"}} _active={{ bg: "rgba(0, 0, 0, 0.1)"}} bg={selectedChat == props.chat ? "rgba(167, 215, 197, 0.5)" : ""} cursor="pointer">
            <Flex gap={2}>
                <Box
                    w="30px"
                    h="30px"
                    borderRadius="full"
                    bg={"white"}
                    mr={2}
                    flexShrink={0}
                    flexGrow={0}  
                    flexBasis="30px"
                />
                <Flex direction={"column"} w={"full"}>
                    <Flex gap={2} justifyContent={"space-between"}>
                        <div>
                            {props.chat.title}
                        </div>
                        <ChatRemoveButton id={props.chat.id}/>
                    </Flex>
                    <Box fontSize={12} maxW="100%" wordBreak={"break-word"} whiteSpace={"pre-line"}>
                        Описание группы
                    </Box>
                </Flex>
                
            </Flex>
        </Box>
    )
}