'use client'
import { createChat } from "@/utils/actions"
import React, { useActionState, useRef, useState } from "react"
import { useCreateChatStore } from "@/utils/store"
import UserSelectMenu from "../../features/user/select-menu"
import { useSession } from "next-auth/react"
import { Box, Button, Flex, Input } from "@chakra-ui/react"

export default function ChatForm()
{
    const {addedUsers, clearAddedUsers} = useCreateChatStore()
    const { data: session, status } = useSession();
    const formRef = useRef<HTMLFormElement>(null)
    const [text, setText] = useState("")

    const handleSubmit = async(e: React.FormEvent) => {
        e.preventDefault()
        const formData = new FormData(e.currentTarget as HTMLFormElement);
          
        formData.append("users_id", JSON.stringify([...addedUsers, session?.user.id]))
        formData.append("title", text)
        setText("")

        clearAddedUsers()
        try
        {
            await createChat({} as any, formData)

            console.log("Chat is created!")
        }
        catch(e)
        {
            console.log("Axios error")
        }
    }

    return(
        <div>
            <form ref={formRef} onSubmit={handleSubmit} method="POST">
                <div className="flex flex-col">
                    <Flex direction={"column"} gap={5}>
                        <Box textAlign={"center"}>Создать чат</Box>
                        <Input size={"2xs"} type="text" value={text} onChange={(e) => setText(e.target.value)} name="title" placeholder="название"/>
                        <UserSelectMenu/>
                        <Button bg={"button"} color={"text"} size={"xs"} type="submit">Создать</Button>
                    </Flex>
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
                </div>
            </form>
        </div>
    )
}