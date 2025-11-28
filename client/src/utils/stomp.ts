import { Client, frameCallbackType, messageCallbackType } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const url:string = process.env.NEXT_PUBLIC_WS_URL || "http://localhost:8083/ws"
const client = new Client({
    webSocketFactory: () => new SockJS(url),
    reconnectDelay: 5000,
})

export function activateStompClient(
    onConnectCallback: frameCallbackType, 
    onErrorCallback: frameCallbackType
) {
  client.onConnect = onConnectCallback;
  client.onStompError = onErrorCallback;

  console.log("Stomp Connected!!!")
  client.activate();
}

export function deactivateStompClient() {
  client.deactivate();
}

export function subscribe(destination: string, callback: messageCallbackType) {
  return client.subscribe(destination, callback);
}

export async function publish(destination: string, body: string, token: string) {
  if (client && client.connected) {
    client.publish({ destination, body, headers: {
      'Authorization': 'Bearer ' + token
    }});
  } else {
    console.warn('STOMP client not connected');
  }
}

export default client;