import { Dispatch, SetStateAction, useEffect, useState } from "react";

const useCloseDropdown = (
  elem: React.MutableRefObject<HTMLElement | null>,
  initialState: boolean,
): [boolean, Dispatch<SetStateAction<boolean>>] => {
  const [isOpen, setIsOpen] = useState(initialState);

  useEffect(() => {
    const onClick = (e: MouseEvent) => {
      if (elem.current !== null && !elem.current.contains(e.target as Node)) {
        setIsOpen(!isOpen);
      }
    };

    if (isOpen) {
      window.addEventListener("click", onClick);
    }

    return () => {
      window.removeEventListener("click", onClick);
    };
  }, [isOpen, elem]);

  return [isOpen, setIsOpen];
};

export default useCloseDropdown;
