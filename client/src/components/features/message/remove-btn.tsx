'use client'

import { deleteMessage } from "@/utils/actions"
import { useStore } from "@/utils/store"
import { Box, Flex } from "@chakra-ui/react"
import { LuBeer } from "react-icons/lu"

export default function MessageRemoveButton(props: {id: string})
{
    const {removeMessage} = useStore()

    const handler = () => {
        removeMessage(props.id) 
        deleteMessage(props.id)
    }

    return(
        <Box onClick={() => handler()}>
            <Flex gap={2}>
                <LuBeer size={15}/>
                <Box>
                    Delete
                </Box>
            </Flex>
        </Box>
    )
}