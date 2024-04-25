import { atom, AtomEffect } from "recoil";
import { UserStateType } from "@/interfaces/user/UserStateType";

const localStorageEffect: (key: string) => AtomEffect<UserStateType> =
  (key) =>
  ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue !== null) {
      setSelf(JSON.parse(savedValue));
    }

    onSet((newValue, _, isReset) =>
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue)),
    );
  };

// user 상태를 로그인일 때는 값이 있고,
// 로그아웃 상태일 때는 null이 들어가도록 설정
const userState = atom<UserStateType>({
  key: "userState",
  default: {
    user: null,
  },
  effects: [localStorageEffect("userInfo")],
});

export default userState;
