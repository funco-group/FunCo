import { useRecoilState } from "recoil";
import followModalState from "@/recoils/followModal";
import { FollowModalType } from "@/interfaces/userPage/FollowModalType";

function useFollowModalState() {
  const [{ followModal }, setFollowModalState] =
    useRecoilState(followModalState);

  return {
    followModal,
    onFollowModal: (newFollowModal: FollowModalType) => {
      setFollowModalState({ followModal: newFollowModal });
    },
    offFollowModal: () => setFollowModalState({ followModal: null }),
  };
}

export default useFollowModalState;
