import axios from "axios";
import { NextAuthConfig } from "next-auth";

export const authConfig = {
  pages: {},
  providers: [],
} satisfies NextAuthConfig;

export const serverInstance = axios.create({
  baseURL: process.env.BASE_URL,
  timeout: 1000,
});