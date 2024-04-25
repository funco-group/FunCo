import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import TabButton from "@/components/common/Button/TabButton.styled";
import FollowTabContainer from "./FollowTab.styled";

function FollowTab() {
  const navigate = useNavigate();
  const location = useLocation();
  const tabNameList = [
    ["팔로잉", "following"],
    ["팔로워", "follower"],
  ];

  const [nowTabName, setNowTabName] = useState<string | null>(
    location.pathname.split("/")[3],
  );

  useEffect(() => {
    if (nowTabName !== location.pathname.split("/")[3]) {
      setNowTabName(location.pathname.split("/")[3]);
    }
  }, [location.pathname, nowTabName]);

  const handleTabClick = (selectedTabName: string) => {
    navigate(`/history/follow/${selectedTabName}`);
    setNowTabName(selectedTabName);
  };

  return (
    <FollowTabContainer>
      {tabNameList.map((tabName) => (
        <TabButton
          key={tabName[1]}
          width="6.25rem"
          height="2.5rem"
          $active={tabName[1] === nowTabName}
          onClick={() => handleTabClick(tabName[1])}
          radius={tabName[1] === "following" ? "left" : "right"}
        >
          {tabName[0]}
        </TabButton>
      ))}
    </FollowTabContainer>
  );
}

export default FollowTab;
