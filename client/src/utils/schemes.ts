import z from "zod";

export const schemeLogin = z.object({
    username: z.string().min(5, 'Слишком короткое имя пользователя.'),
    password: z.string().min(8, 'Пароль должен быть длинее.')
})