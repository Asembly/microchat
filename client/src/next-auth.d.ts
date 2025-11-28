import type { DefaultUser } from 'next-auth';
import { DefaultJWT } from 'next-auth/jwt';

declare module 'next-auth' {
  interface Session {
    user: DefaultUser & {
      id: string
      name: string
    }
    access?: AccessToken 
    refresh?: RefreshToken
  }
  interface User{
    id: string
    name: string
    access?: AccessToken 
    refresh?: RefreshToken 
  }
}

declare module 'next-auth/jwt' {
  interface JWT{
    uid: string
    access?: AccessToken 
    refresh?: RefreshToken 
    expires_at?: number 
  }
}