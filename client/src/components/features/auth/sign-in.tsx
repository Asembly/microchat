'use client'
import { signUp } from "@/utils/actions";
import { schemeLogin } from "@/utils/schemes";
import { Box, Button, Field, Flex, Input, Link, Stack, Text, VStack } from "@chakra-ui/react";
import { signIn } from "next-auth/react";
import { redirect, useSearchParams } from "next/navigation";
import { useActionState, useState } from "react";

export default function SignIn()
{
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [errors, setErrors] = useState({username: '', password: ''})

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const formData = {
            username: username,
            password: password
        }
        const result = schemeLogin.safeParse(formData)
        if(!result.success)
        {
            setErrors(
                {
                    username: result.error.format().username?._errors.join(", ") || "", 
                    password: result.error.format().password?._errors.join(", ") || "",
                })
            return;
        }
        await signIn("credentials", { username, password, redirect: false ,callbackUrl: "/" }); 
        redirect('/')
    };

    return(
        <form onSubmit={handleSubmit}>
            <Stack gap="8" maxW="sm" css={{ "--field-label-width": "96px" }} >
                <Field.Root orientation="horizontal">
                    <Flex direction={"column"} gap={3}>
                    <Flex>
                        <Field.Label >Имя</Field.Label>
                        <Input name="username" value={username} borderColor={errors.username.length != 0 ? "fg.error" : ""} onChange={(e) => setUsername(e.target.value)} placeholder="введите имя" flex="1" />
                    </Flex>
                    <Box color={"error"} fontSize={"sm"}>
                        {errors.username}
                    </Box>
                   </Flex>
                </Field.Root>

                <Field.Root orientation="horizontal">
                    <Flex direction={"column"} gap={3}>
                        <Flex direction={"row"}>
                            <Field.Label>Пароль</Field.Label>
                            <Input name="password" type="password" borderColor={errors.password.length != 0 ? "fg.error" : ""} value={password} onChange={(e) => setPassword(e.target.value)} placeholder="введите пароль" flex="1" />
                        </Flex>
                        <Box color={"error"} fontSize={"sm"}>
                            {errors.password}
                        </Box>
                    </Flex>
                </Field.Root>

                <Field.Root orientation="horizontal" >
                    <Button type="submit" w={"full"} bg={"button"} color={"text"}>Авторизоваться</Button>
                </Field.Root>

                <Field.Root orientation="horizontal" flexDirection={'row'} justifyContent={'center'}>
                    <Text>
                        нет аккаунта? {" "} 
                        <Link
                            href="/sign-up"
                            color={"accent"}
                        >
                            зарегистрироваться
                        </Link>
                    </Text>
                </Field.Root>
            </Stack>
        </form>
    )
}