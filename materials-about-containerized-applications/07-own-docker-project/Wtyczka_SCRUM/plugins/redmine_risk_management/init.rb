# plugins/redmine_risk_management/init.rb
Redmine::Plugin.register :redmine_risk_management do
  name 'Wtyczka Zarządzania Ryzykiem'
  author 'Monia'
  description 'Ta wtyczka wspiera identyfikację, ocenę i monitorowanie ryzyk projektowych.'
  version '0.1.0'
  url 'http://localhost:3000/redmine_risk_management'
  author_url 'https://twoje-repozytorium-github'
  
  # Rejestracja uprawnień
  project_module :risk_management do
    permission :view_risks, { risks: [:index] }, public: true
    permission :manage_risks, { risks: [:new, :edit, :delete] }
  end

  # Dodanie pozycji menu do menu projektu
  menu :project_menu, :risks, { controller: 'risks', action: 'index' }, caption: 'Ryzyka', after: :activity, param: :project_id

end
