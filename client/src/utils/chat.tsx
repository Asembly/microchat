'use client'
import { activateStompClient, deactivateStompClient, subscribe } from "@/utils/stomp";
import { useStore } from "@/utils/store";
import { useEffect } from "react";

export default function StompChat()
{
    const { addMessage, selectedChat } = useStore() 

    useEffect(() => {
        if (!selectedChat) return;
        activateStompClient(
            () => {
                console.log("Connected to WebSocket STOMP server")
                subscribe(`/topic/chat/${selectedChat.id}`, (message: any) => {
                    console.log("Recieved message: " + message.body)
                    addMessage(JSON.parse(message.body));
                })
            },
            (error) => {
                console.error("Broker error: ", error.headers["message"]);
                console.error("Details: ", error.body);
            }
        );

        return () => {
            deactivateStompClient();
        };
    }, [selectedChat]);

    return null
}