class Risk < ActiveRecord::Base
  belongs_to :project
  belongs_to :assignee, class_name: 'User', optional: true

  has_many :spike_tasks, dependent: :destroy

  validates :title, :likelihood, :impact, :category, :status, :response, presence: true

  CATEGORIES = %w[Techniczne Organizacyjne Zasoby Zespół Inne]
  STATUSES = %w[Aktywne Nieaktywne]
  RESPONSES = %w[Mitygacja Unikanie Akceptacja Przeniesienie]

   # Dodanie metody priority
   def priority
    likelihood * impact
  end
  
end
