'use client'
import { Box, Flex } from "@chakra-ui/react";
import MessagesList from "../widgets/message/messages-list";
import ChatsList from "../widgets/chat/chats-list";
import UsersList from "../widgets/user/users-list";
import ChatForm from "../widgets/chat/chat-form";
import UserForm from "../widgets/user/user-form";
import { useScreen } from "../ui/screen-provider";
import ChatSidebar from "./chat/chat-sidebar";
import UserSidebar from "./user/user-sidebar";
import UserSelectMenu from "../features/user/select-menu";

export default function Body()
{

    const {isMobile} = useScreen()

    console.log(isMobile)

    return(
        <Flex
        flex="1"
        width="100%"
        height="100%"
        minHeight="0" 
        minWidth="0"
        bg="myBg"
        >
            <ChatSidebar/>
            
            <Flex
                flex="1"
                flexDirection="column"
                align="center"
                justify="center"
                p={4}
                minWidth="0"
                overflowY="auto"
                bg="myBgAlt"
            >
                <MessagesList/>
            </Flex>

            <UserSidebar/>
        </Flex>
    )
}