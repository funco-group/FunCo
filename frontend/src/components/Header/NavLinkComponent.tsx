import Link from 'next/link'
import { usePathname } from 'next/navigation'

interface NavLinkProps {
  path: string
  name: string
}
function NavLinkComponent({ path, name }: NavLinkProps) {
  const nowTabName = usePathname()
  const isActive = nowTabName === path

  return (
    <Link href={path} className={isActive ? 'nav-link active' : 'nav-link'}>
      {name}
    </Link>
  )
}

export default NavLinkComponent
