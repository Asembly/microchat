import { Container, Flex } from "@chakra-ui/react";

export default function AuthLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <Flex bg={"myBg"} alignItems={"center"} height={"dvh"} justifyContent={"center"}>
        {children}
    </Flex>
    
  );
}