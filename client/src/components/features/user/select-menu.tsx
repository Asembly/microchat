'use client'

import { useCreateChatStore } from "@/utils/store"
import { Button, Flex, Input } from "@chakra-ui/react"
import { useState } from "react"

export default function UserSelectMenu()
{

    const [text, setText] = useState("")
    const {setAddedUsers, addedUsers} = useCreateChatStore()

    const buttonHandler = () => {
        console.log(text)
        setAddedUsers(text)
        setText("")
    }

    const handleKeyPress = (event: any) => {
        if (event.key === 'Enter') {
            event.preventDefault(); 
            setAddedUsers(text)
            setText("")
        }
    }

    const inputHandler = (text: string) => {
        setText(text)
        console.log(text)
    }

    return(
        <Flex alignItems={"center"} gap={2}>
            <Input size={"2xs"} onKeyPress={handleKeyPress} type="text" value={text} onChange={(e) => inputHandler(e.target.value)} placeholder="id пользователя"/>
            <Button size={"2xs"} fontSize={"2xs"} disabled={text.length < 8 || text.length > 8 } onClick={buttonHandler} type="button">добавить</Button>
        </Flex>
    )
}