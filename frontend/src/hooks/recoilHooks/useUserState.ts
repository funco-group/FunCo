import { useRecoilState, useRecoilValue, useResetRecoilState } from 'recoil'
import { userState, userUnReadCount } from '@/recoils/user'
import { UserType } from '@/interfaces/user/UserType'

function useUserState() {
  const [{ user }, setUserState] = useRecoilState(userState)
  const resetUser = useResetRecoilState(userState)
  const unReadCount = useRecoilValue(userUnReadCount)

  return {
    user,
    unReadCount,
    login: (newUser: UserType) => {
      setUserState({ user: newUser })
    },
    logout: () => {
      resetUser()
    },
    updateUnReadNoti: (num: number) => {
      if (user) {
        setUserState({ user: { ...user, unReadCount: num } })
      }
    },
  }
}

export default useUserState
