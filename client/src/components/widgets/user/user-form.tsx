'use client'
import { useCreateChatStore, useStore } from "@/utils/store";
import { useActionState, useRef } from "react";
import UserSelectMenu from "../../features/user/select-menu";
import { addUserToChat } from "@/utils/actions";
import { Box, Button, Flex } from "@chakra-ui/react";

export default function UserForm()
{

    const [_, formAction] = useActionState(addUserToChat, {} as any)
    const {addedUsers, clearAddedUsers} = useCreateChatStore()
    const {selectedChat} = useStore()
    const formRef = useRef<HTMLFormElement>(null)

    const handleSubmit = async(e: React.FormEvent) => {
        e.preventDefault()
        const formData = new FormData(e.currentTarget as HTMLFormElement);
          
        formData.append("users_id", JSON.stringify(addedUsers))
        formData.append("chat_id", selectedChat.id) 

        clearAddedUsers()
        try
        {
            await addUserToChat({} as any, formData)
            console.log("Chat is created!")
        }
        catch(e)
        {
            console.log("Axios error")
        }

    }

    return (
        <div>
            <form ref={formRef} onSubmit={handleSubmit} method="POST">
                <div className="flex flex-col">
                    <Flex direction={"column"} gap={5}>
                        <Box textAlign={"center"}>Добавить пользователей</Box>
                        <UserSelectMenu/>
                        <Button bg={"button"} color={"text"} size={"xs"} type="submit">Добавить</Button>
                        <Box>
                            {
                                addedUsers.map((item,i) => (
                                    <div key={i}>
                                        {item}
                                    </div>
                                )
                                )
                            }
                        </Box>
                    </Flex>
                </div>
            </form>
        </div>
    )
}