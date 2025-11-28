'use client'
import { Flex, Text, Box } from "@chakra-ui/react";
import { useSession } from "next-auth/react";
import { useState, useRef } from "react";
import SendButton from "../features/message/send-message";
import { ColorModeButton } from "../ui/color-mode";
import { useStore } from "@/utils/store";

export default function Footer() {
  const { data: session } = useSession();
  const [copied, setCopied] = useState(false);
  const leftRef = useRef(null);

  const username = session?.user?.name || "User";
  const userId = session?.user?.id || "unknown";
  const {selectedChat} = useStore();

  const handleCopy = async () => {
    if (userId) {
      await navigator.clipboard.writeText(userId);
      setCopied(true);
      setTimeout(() => setCopied(false), 1000);
    }
  };

  return (
    <Box position="relative" w="100%">
      <Flex
        flexShrink={0}
        bg="topBar"
        h="60px"
        align="center"
        px={4}
        gap={2}
        w="100%"
        justifyContent={"space-between"}
      >
        <Box position="relative" ref={leftRef} minW="0" maxW={"min-content"} flex={"0 0 auto"} mdDown={{display:"none"}}>
          <Flex
            align="center"
            gap={2}
            cursor="pointer"
            userSelect="none"
            onClick={handleCopy}
            minW={0}
          >
            <Text
              truncate
              fontWeight="bold"
              color="text"
              maxW="120px"
            >
              {username}
            </Text>
            <Text
              color={copied ? "nickOwn" : "accent"}
              fontSize="sm"
              fontFamily="mono"
              truncate
              maxW="80px"
              transition="color 0.2s"
            >
              #{userId}
            </Text>
          </Flex>
          {copied && (
            <Box
              position="absolute"
              left={0}
              top="-36px"
              zIndex={10}
              px={2}
              py={1}
              bg="accent"
              color="white"
              borderRadius="md"
              fontSize="sm"
              boxShadow="md"
              pointerEvents="none"
              minW="max-content"
              textAlign="center"
            >
              ID скопирован!
            </Box>
          )}
        </Box>

        <Flex flex="0.5" justify="center" mdDown={{flex:"1"}}>
          {
            selectedChat.id == undefined
            ?
            ""
            :
            <SendButton/>
          }
        </Flex>
        <Flex minW="60px" flex={"0 0 auto"} justify="flex-end" mdDown={{display:"none"}}>
            <Box>
                <ColorModeButton/>
            </Box>
        </Flex>

      </Flex>
    </Box>
  );
}
