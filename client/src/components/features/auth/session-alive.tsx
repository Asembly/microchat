'use client'
import { useStore } from "@/utils/store";
import { getSession } from "next-auth/react";
import { useEffect } from "react";

export function SessionKeepAlive({ interval = 5 * 60 * 1000 }) {
  const {setSelectedChat} = useStore()
  useEffect(() => {
    const timer = setInterval(() => {
      getSession(); 
    }, interval);

    return () => clearInterval(timer);
  }, [interval]);

  return null;
}