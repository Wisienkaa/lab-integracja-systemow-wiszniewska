# app/models/sprint.rb
class Sprint < ActiveRecord::Base
  has_and_belongs_to_many :projects  # Relacja wielu do wielu z projektem

  validates :name, :start_date, :due_date, presence: true
  validate :dates_are_valid

  private

  def dates_are_valid
    if start_date.present? && due_date.present? && start_date > due_date
      errors.add(:start_date, 'nie może być późniejsza niż data zakończenia')
    end
  end
end
