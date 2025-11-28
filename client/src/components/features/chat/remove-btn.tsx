'use client'

import { deleteChat } from "@/utils/actions"
import { useStore } from "@/utils/store"


export default function ChatRemoveButton(props: {id: string})
{
    const {removeChat} = useStore()

    const handler = () => {
        removeChat(props.id) 
        deleteChat(props.id)
    }

    return(
        <div>
            <button onClick={() => handler()}>X</button>
        </div>
    )
}