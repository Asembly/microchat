import { SessionKeepAlive } from "@/components/features/auth/session-alive";
import { Provider } from "@/components/ui/provider";
import { ScreenProvider } from "@/components/ui/screen-provider";
import { Container, Theme } from "@chakra-ui/react";
import { SessionProvider } from "next-auth/react";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  return (
    <html suppressHydrationWarning>
      <body>
            <Provider>
              <Theme>
                <SessionProvider>
                  <SessionKeepAlive/>
                      {children}
                </SessionProvider>
              </Theme>
            </Provider>
      </body>
    </html>
  );
}
