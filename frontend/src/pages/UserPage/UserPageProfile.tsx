import { useEffect, useState } from "react";
import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import {
  ProfileButtonDiv,
  ProfileDetailContainer,
  ProfileEditButtonDiv,
  ProfileInput,
  ProfileRankDiv,
  ProfileRankFlexDiv,
  ProfileRankOuterDiv,
  ProfileTextArea,
  UserPageProfileContainer,
  NicknameDiv,
  IntroductionDiv,
} from "./UserPageProfile.styled";
import { ComponentTitleH3 } from "./styled";
import MemberType from "@/interfaces/userPage/MemberType";
import useFollowModalState from "@/hooks/recoilHooks/useFollowModalState";
import medalMap from "@/lib/medalMap";
import PortfolioModal from "./PortfolioModal";
import { editIntroduction, editNickname } from "@/apis/member";

interface UserPageProfileProps {
  isCurrentUser: boolean;
  member: MemberType;
}

function UserPageProfile({ isCurrentUser, member }: UserPageProfileProps) {
  const [nickname, setNickname] = useState(member.nickname);
  const [isEditNickname, setIsEditNickname] = useState(false);
  const [introduction, setIntroduction] = useState(member.introduction);
  const [isEditIntro, setIsEditIntro] = useState(false);
  const { onFollowModal } = useFollowModalState();
  const [onFollowAssetModal, setOnFollowAssetModal] = useState(false);

  useEffect(() => {
    setNickname(member.nickname);
    if (!member.introduction) {
      setIntroduction("한 줄 소개를 입력해주세요!");
    } else {
      setIntroduction(member.introduction);
    }
  }, [member]);

  const handleNicknameInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };
  const handleNicknameEditClick = async () => {
    if (isEditNickname) {
      await editNickname(nickname);
    }
    setIsEditNickname((prev) => !prev);
  };
  const handleIntroInput = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduction(e.target.value);
  };
  const handleIntroEditClick = async () => {
    if (isEditIntro) {
      await editIntroduction(introduction);
    }
    setIsEditIntro((prev) => !prev);
  };

  const handleFollowClick = () => {
    onFollowModal({
      memberId: member.memberId,
    });
  };

  const handlePortFolioClick = () => {
    setOnFollowAssetModal((prev) => !prev);
  };

  function renderButton() {
    if (isCurrentUser) {
      return (
        <ProfileEditButtonDiv>
          <BrandButtonComponent
            content={isEditNickname ? "닉네임 저장" : "닉네임 수정"}
            color={null}
            cancel={false}
            onClick={handleNicknameEditClick}
            disabled={false}
          />
          <BrandButtonComponent
            content={isEditIntro ? "한 줄 소개 저장" : "한 줄 소개 수정"}
            color={null}
            cancel={false}
            onClick={handleIntroEditClick}
            disabled={false}
          />
        </ProfileEditButtonDiv>
      );
    }
    if (!member.isFollow) {
      return (
        <BrandButtonComponent
          content="팔로우"
          color={null}
          cancel={false}
          onClick={handleFollowClick}
          disabled={false}
        />
      );
    }
    return (
      <BrandButtonComponent
        content="포트폴리오 보기"
        color={null}
        cancel={false}
        onClick={handlePortFolioClick}
        disabled={false}
      />
    );
  }
  return (
    <UserPageProfileContainer>
      {onFollowAssetModal && (
        <PortfolioModal
          memberId={member.memberId}
          nickname={member.nickname}
          handlePortFolioClick={handlePortFolioClick}
        />
      )}
      <ComponentTitleH3>프로필</ComponentTitleH3>
      <ProfileDetailContainer>
        <img src={member.profileUrl} alt="member-profile" />
        <NicknameDiv>
          {isEditNickname ? (
            <ProfileInput
              type="text"
              value={nickname}
              onChange={handleNicknameInput}
              maxLength={15}
            />
          ) : (
            nickname
          )}
        </NicknameDiv>
        <ProfileRankFlexDiv>
          <ProfileRankOuterDiv>
            <div>총 자산 랭킹</div>
            <ProfileRankDiv>
              {member.assetRank ? (
                <>
                  <span>{medalMap.get(member.assetRank) || "🏃‍♂️"}</span>
                  {member.assetRank}
                </>
              ) : (
                "-"
              )}
              위
            </ProfileRankDiv>
          </ProfileRankOuterDiv>
          <ProfileRankOuterDiv>
            <div>총 팔로워 랭킹</div>
            <ProfileRankDiv>
              {member.followingCashRank ? (
                <>
                  <span>{medalMap.get(member.followingCashRank) || "🏃‍♂️"}</span>
                  {member.followingCashRank}
                </>
              ) : (
                "-"
              )}
              위
            </ProfileRankDiv>
          </ProfileRankOuterDiv>
        </ProfileRankFlexDiv>
        <IntroductionDiv>
          {isEditIntro ? (
            <ProfileTextArea
              value={introduction}
              onChange={handleIntroInput}
              maxLength={21}
            />
          ) : (
            <div>{introduction}</div>
          )}
        </IntroductionDiv>
      </ProfileDetailContainer>
      <ProfileButtonDiv>{renderButton()}</ProfileButtonDiv>
    </UserPageProfileContainer>
  );
}

export default UserPageProfile;
