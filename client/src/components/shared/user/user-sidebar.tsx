import AddUserBtn from "@/components/features/chat/add-user-btn";
import UserSelectMenu from "@/components/features/user/select-menu";
import UsersList from "@/components/widgets/user/users-list";
import { Box, Flex } from "@chakra-ui/react";

export default function UserSidebar()
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
                Участники
            </Box>

            <AddUserBtn/>

            <Flex direction="column" gap={4} w="100%" align="center">
                <UsersList/>
            </Flex>
        </Box>
    )
}