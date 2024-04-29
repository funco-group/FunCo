import { selector } from "recoil";
import userState from "./atom";

const userUnReadCount = selector({
  key: "userUnReadCount",
  get: ({ get }) => {
    const { user } = get(userState);
    return user?.unReadCount;
  },
});

export default userUnReadCount;
