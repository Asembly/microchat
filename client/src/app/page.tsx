import StompChat from "@/utils/chat";
import Body from "@/components/shared/body";
import Footer from "@/components/shared/footer";
import Header from "@/components/shared/header";
import { Flex } from "@chakra-ui/react";

export default function Home() {
  return (
    <div> 
      <StompChat/>
      <div className="flex">
        <Flex direction="column" height="100vh" width="100vw">
          <Header/>
          <Body/>
          <Footer/>
        </Flex>
      </div>
    </div>
  );
}
