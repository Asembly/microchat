'use client'
import { logout } from "@/utils/actions";
import { CloseButton } from "@chakra-ui/react";
import { useSession } from "next-auth/react";

export default function LogoutBtn()
{
    const { data:session } = useSession()
    return(
        <CloseButton onClick={() => logout(session?.refresh?.token)}/>
    )
}