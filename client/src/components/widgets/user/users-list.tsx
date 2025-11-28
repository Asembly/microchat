'use client'
import { getUsersByChatId } from "@/utils/actions"
import { useEffect, useState } from "react";
import { useStore } from "@/utils/store";
import { Box } from "@chakra-ui/react";

export default function UsersList()
{

    const [users, setUsers] = useState<User[]>([])
    const { selectedChat } = useStore();

    console.log(users + "Пользователи в чате")

    useEffect(() => {
        getUsersByChatId(selectedChat.id).then(setUsers)
        console.log(selectedChat.id + "Selected chat") 
    }, [selectedChat]) 

    return(
        <Box>
            {
                users.map((item) => (
                    <div key={item.id} className="flex gap-5">
                        <div className="flex gap-5">
                            <div>
                                {item.username}
                            </div>
                        </div>
                    </div>
                ))
            }
        </Box>
    )
}
