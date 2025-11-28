import NextAuth from "next-auth";
import { NextRequest, NextResponse } from "next/server";
import { authConfig } from "./utils/config";
import { auth } from "./utils/auth";

export default NextAuth(authConfig).auth;

export async function middleware(request: NextRequest)
{
    const {pathname} = request.nextUrl;
    const session = await auth()

    const authPath = ["/sign-in", "/sign-up"]

    if(session)
    {
      console.log("Пользователь успешно авторизовался на клиенте: " + session.user.name)    
    }

    if(!authPath.includes(pathname) && session?.refresh == undefined)
    {
        console.log("Пользователь не авторизован, выполнена переадресация.")
        return NextResponse.redirect(new URL('/sign-in',request.url))
    }
    else if(session && authPath.includes(pathname))
    {
      console.log("Пользователь авторизован, выполнена переадресация.")
      return NextResponse.redirect(new URL("/",request.url))
    }

}

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};