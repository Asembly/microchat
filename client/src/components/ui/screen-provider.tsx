'use client'
import React, { useState, useEffect, createContext, useContext, ReactNode } from "react";

const ScreenContext = createContext({ isMobile: false });

interface ScreenProviderProps {
  children: ReactNode;
  mobileBreakpoint?: number;
}

export function useScreen() {
  return useContext(ScreenContext);
}

export function ScreenProvider({ children, mobileBreakpoint = 800 }: ScreenProviderProps) {
  const [isMobile, setIsMobile] = useState(
    typeof window !== "undefined" ? window.innerWidth < mobileBreakpoint : false
  );

  useEffect(() => {
    function handleResize() {
      setIsMobile(window.innerWidth < mobileBreakpoint);
    }
    window.addEventListener("resize", handleResize);
    handleResize();
    return () => window.removeEventListener("resize", handleResize);
  }, [mobileBreakpoint]);

  return (
    <ScreenContext.Provider value={{ isMobile }}>
      {children}
    </ScreenContext.Provider>
  );
}