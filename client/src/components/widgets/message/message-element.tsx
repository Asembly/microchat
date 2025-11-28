'use client'
import MessageRemoveButton from "@/components/features/message/remove-btn";
import { Box, Flex, Menu, Portal, Text } from "@chakra-ui/react";
import { useSession } from "next-auth/react";
import { LuBeer, LuEllipsis } from "react-icons/lu";

export default function MessageElement(props: {msg: Message})
{

    const { data: session, status } = useSession();

    return(
        <Flex
            gap={2}
            align="flex-start"
            w="full"
            px={4}
            py={2}
            _hover={{ bg: "rgba(0, 0, 0, 0.03)"}}
          >
            <Box
              w="30px"
              h="30px"
              borderRadius="full"
              bg={"white"}
              mr={2}
            />
            <Box  flex="1" color={"msgText"}>
              <Flex align="center" mb={1} gap={2}>
                <Text fontWeight="bold" color={session?.user.name == props.msg.author ? "nickOwn" :"nickForeign"} fontSize="md">
                  {props.msg.author}
                </Text>
              </Flex>
              <Text fontSize="sm" whiteSpace="pre-line" wordBreak={"break-word"} color={"text"}>
                {props.msg.text}
              </Text>
              
            </Box> 
            {/* <MessageRemoveButton id={props.msg.id} /> */}
            <Box _hover={{cursor: "pointer"}} mt={1} color={"msgText"}>
              <Menu.Root>
              <Menu.Trigger asChild>
                <LuEllipsis size={18} />
              </Menu.Trigger>
              <Portal>
                <Menu.Positioner>
                  <Menu.Content>
                    <Menu.Item display={session?.user.name == props.msg.author ?"" : "none"} value="new-txt-a" _hover={{cursor: "pointer"}} mt={1} color={"msgText"}>
                      <MessageRemoveButton id={props.msg.id}/>
                    </Menu.Item>
                  </Menu.Content>
                </Menu.Positioner>
              </Portal>
            </Menu.Root>
            </Box>
            {/* <Text fontSize="sm" whiteSpace="pre-line" wordBreak={"break-word"} color={"text"}>
                {props.msg.created_at}
            </Text> */}
          </Flex>
    )
}