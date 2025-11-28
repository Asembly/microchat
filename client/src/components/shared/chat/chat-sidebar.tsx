import CreateChatBtn from "@/components/features/chat/create-chat-btn";
import ChatsList from "@/components/widgets/chat/chats-list";
import { Box, Flex } from "@chakra-ui/react";

export default function ChatSidebar()
{
    return (
        <Box
            width="15vw"
            minWidth="180px"
            bg="transparent"
            p={0}                  
            overflowY="auto"
            display="flex"
            flexDirection="column"
            alignItems="center"
            mdDown={{display:"none"}}
        >
            <Box mt={8} mb={4} fontSize="xl" textAlign="center" fontWeight="bold">
                Чаты
            </Box>

            <CreateChatBtn/>

            <Flex direction="column" gap={4} w="100%" align="center">
                <ChatsList/>
            </Flex>
        </Box>

    )
}
