import { Dispatch, SetStateAction, useEffect, useState } from "react";
import {
  NotiDropdownContainer,
  NotiHistoryContentContainer,
  NotiMoreButton,
} from "./NotiDropdown.styled";
import NotiHistoryType from "@/interfaces/notification/NotiHistoryType";
import NotiHistoryContent from "./NotiHistoryContent";
import downArrow from "@/assets/icon/chevron-down.svg";
import { getNotiHistoryList, sendReadNotiList } from "@/apis/notification";
import useUserState from "@/hooks/recoilHooks/useUserState";

interface NotiDropdownProps {
  visible: boolean;
  setUnReadCount: Dispatch<SetStateAction<number | undefined>>;
}

function NotiDropdown({ visible, setUnReadCount }: NotiDropdownProps) {
  const [notiHistoryList, setNotiHistoryList] = useState<NotiHistoryType[]>();
  const [open, setOpen] = useState(false);
  const { checkUnReadNoti } = useUserState();

  useEffect(() => {
    if (visible) {
      setOpen(true);
      getNotiHistoryList((res) => {
        const { data } = res;
        setNotiHistoryList(data);
      });
    } else {
      setTimeout(() => setOpen(false), 150);
    }
  }, [visible]);

  useEffect(() => {
    if (notiHistoryList !== undefined) {
      sendReadNotiList(() => {
        setUnReadCount(0);
        checkUnReadNoti();
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [notiHistoryList]);

  if (!open) {
    return null;
  }

  return (
    <NotiDropdownContainer $visible={visible}>
      <NotiHistoryContentContainer>
        {notiHistoryList ? (
          notiHistoryList.map((notiHistory) => (
            <NotiHistoryContent
              key={notiHistory.id}
              notiHistory={notiHistory}
            />
          ))
        ) : (
          <>노티없어영~~~</>
        )}
      </NotiHistoryContentContainer>
      <NotiMoreButton>
        <p>더보기</p>
        <img src={downArrow} alt="more-icon" />
      </NotiMoreButton>
    </NotiDropdownContainer>
  );
}

export default NotiDropdown;
