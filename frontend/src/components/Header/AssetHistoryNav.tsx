import useLoginAlertModalState from '@/hooks/recoilHooks/useLoginAlertModalState'
import { userState } from '@/recoils/user'
import { usePathname, useRouter } from 'next/navigation'
import { useRecoilValue } from 'recoil'
import { NavComponent } from '@/containers/HeaderContainer/styled'

interface NavLinkProps {
  path: string
  name: string
}

function AssetHistoryNav({ path, name }: NavLinkProps) {
  const nowTabName = usePathname()
  const isActive = nowTabName === path

  const { onLoginAlertModal } = useLoginAlertModalState()
  const user = useRecoilValue(userState)

  const router = useRouter()

  const clickNav = () => {
    if (user.user) {
      router.push(path)
    } else {
      onLoginAlertModal()
    }
  }

  return (
    <NavComponent onClick={clickNav} $active={isActive}>
      {name}
    </NavComponent>
  )
}

export default AssetHistoryNav
