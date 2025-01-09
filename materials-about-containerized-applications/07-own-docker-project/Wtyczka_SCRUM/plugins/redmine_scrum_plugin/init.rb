# plugins/redmine_scrum_plugin/init.rb

Redmine::Plugin.register :redmine_scrum_plugin do
  name 'Scrum Plugin'
  author 'Wisienkaa66'
  description 'Jest to moja pierwsza wtyczka i ma ona za zadanie wspomagać zespoły pracujące w metodyce SCRUM dodając nową funkcję, czyli backlog ryzyka'
  version '0.0.1'
  url 'http://localhost:3000/redmine_scrum_plugin'
  author_url 'https://github.com/Wisienkaa/lab-integracja-systemow-wiszniewska.git'
  require_dependency 'my_plugin/hooks'

end
