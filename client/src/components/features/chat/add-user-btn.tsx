import UserForm from "@/components/widgets/user/user-form";
import { Box, Button, CloseButton, Dialog, Menu, Portal } from "@chakra-ui/react";
import UserSelectMenu from "../user/select-menu";

export default function AddUserBtn()
{
    return (
        <Box w={"100%"} display="flex" justifyContent="center">
            <Dialog.Root size={{ mdDown: "full", md: "lg" }}>
                <Dialog.Trigger asChild>
                    <Button
                                    mb={6}
                                    px={2}
                                    bg="button"
                                    rounded="xl"
                                    textAlign="center"
                                    fontSize="md"
                                    py={2}
                                    w={"90%"}
                                    color={"text"}
                                    cursor="pointer"
                                >
                                    Пригласить
                                </Button>
                </Dialog.Trigger>
                <Portal>
                    <Dialog.Backdrop />
                    <Dialog.Positioner>
                    <Dialog.Content bg={"myBg"}>
                        <Dialog.Header>
                        <Dialog.Title>Приглашения</Dialog.Title>
                        </Dialog.Header>
                        <Dialog.Body>
                            <UserForm/>
                        </Dialog.Body>
                        <Dialog.CloseTrigger asChild>
                        <CloseButton size="sm" />
                        </Dialog.CloseTrigger>
                    </Dialog.Content>
                    </Dialog.Positioner>
                </Portal>
            </Dialog.Root>
        </Box>
    )
}