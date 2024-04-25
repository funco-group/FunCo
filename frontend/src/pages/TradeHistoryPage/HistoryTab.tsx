import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { TabContainer, TabItemDiv } from "@/components/crypto/Tab.style";
// import useUserState from "@/hooks/recoilHooks/useUserState";

function HistoryTab() {
  const navigate = useNavigate();
  const location = useLocation();
  // const { user } = useUserState();
  const tabList = [
    ["보유자산", "asset"],
    ["투자손익", "result"],
    ["자산변동내역", "change"],
    ["팔로우", "follow"],
    ["미체결", "orders"],
  ];

  const [nowTabName, setNowTabName] = useState<string | null>(
    location.pathname.split("/")[2],
  );

  useEffect(() => {
    if (nowTabName !== location.pathname.split("/")[2]) {
      setNowTabName(location.pathname.split("/")[2]);
    }
  }, [location.pathname, nowTabName]);

  const handleTabClick = (selectedTabName: string) => {
    navigate(`/history/${selectedTabName}`);
    setNowTabName(selectedTabName);
  };

  return (
    <TabContainer columns={5}>
      {tabList.map((tab) => (
        <TabItemDiv
          key={tab[1]}
          $active={tab[1] === nowTabName}
          onClick={() => handleTabClick(tab[1])}
        >
          {tab[0]}
        </TabItemDiv>
      ))}
    </TabContainer>
    // <HistoryTabContainer>
    //   {tabList.map((tab) => (
    //     <HistoryTabDiv
    //       key={tab[1]}
    //       $active={tab[1] === nowTabName}
    //       onClick={() => handleTabClick(tab[1])}
    //     >
    //       {tab[0]}
    //     </HistoryTabDiv>
    //   ))}
    // </HistoryTabContainer>
  );
}

export default HistoryTab;
